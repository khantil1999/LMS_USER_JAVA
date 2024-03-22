package com.user.lms.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.lms.entity.Booking;
import com.user.lms.entity.BookingStatus;
import com.user.lms.entity.User;
import com.user.lms.entity.VehicleList;
import com.user.lms.models.BookingModel;
import com.user.lms.models.BookingSaveModel;
import com.user.lms.models.CancelBookingModel;
import com.user.lms.models.ConfirmBookingModel;
import com.user.lms.repository.BookingRepository;
import com.user.lms.repository.UserRepository;
import com.user.lms.repository.VehicleListRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleListRepository vehicleListRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;




    public String saveBooking(BookingSaveModel bookingSaveModel, Principal principal) {
        User customer = this.userRepository.findExistingUser(principal.getName());
        VehicleList vehicle = this.vehicleListRepository.getReferenceById(bookingSaveModel.getVehicleId());
        if (customer != null && vehicle != null) {
            Booking booking = new Booking();
            booking.setBookingDate(bookingSaveModel.getBookingDate());
            booking.setStartDestination(bookingSaveModel.getStartDestination());
            booking.setEndDestination(bookingSaveModel.getEndDestination());
            booking.setKm(bookingSaveModel.getKm());
            booking.setGoodsType(bookingSaveModel.getGoodsType());
            booking.setDriver(vehicle.getDriver());
            booking.setVehicleList(vehicle);
            booking.setUser(customer);
            booking.setNoOfLabourers((int) bookingSaveModel.getNoOfLabourers());
            booking.setStatus(BookingStatus.PROVIDER_PENDING);

            booking = this.bookingRepository.saveAndFlush(booking);
            this.sendBookingPlacedEmailToTruckProvider(booking);
            this.sendBookingPlacedEmailToCustomer(booking);

        }
        return "done";
    }

    public List<BookingModel> getBookingByCurrentUser(Principal principal) {
        User customer = this.userRepository.findExistingUser(principal.getName());
        return this.bookingRepository.getBookingByUser(customer.getId())
                .stream().map(BookingModel::fromEntity).collect(Collectors.toList());
    }

    public BookingModel getBookingById(String id) {
        Booking booking = this.bookingRepository.getReferenceById(Long.parseLong(id));
        return BookingModel.fromEntity(booking);

    }

    public String confirmBooking(ConfirmBookingModel confirmBookingModel) {
        Booking booking = this.bookingRepository.getReferenceById((confirmBookingModel.getBookingId()));
        if (booking != null) {
            booking.setIsCustApproved(true);
            booking.setStatus(BookingStatus.PARTIAL_PAYMENT_SEND_BY_CUSTOMER);
            booking.setBookingConfirmPaymentImagePath(confirmBookingModel.getPaymentConfirmImagePath());
            booking= this.bookingRepository.saveAndFlush(booking);
            this.sendBookingConfirmEmailToTruckProvider(booking);
        }
        return "Done";
    }


    public String cancelBooking(String bookingId, CancelBookingModel cancelBookingModel){
        Booking booking = this.bookingRepository.getReferenceById(Long.parseLong(bookingId));
        if(booking != null){
            booking.setStatus(BookingStatus.CANCEL);
            booking.setIsCustApproved(false);
            booking = this.bookingRepository.saveAndFlush(booking);
            System.out.println(cancelBookingModel);
            if(cancelBookingModel.getIsChargesApplied()){
                this.sendBookingCancelEmailWithChargesToCustomer(booking,cancelBookingModel);
                this.sendBookingCancelEmailWithChargesToProvider(booking,cancelBookingModel);
            }else{
                this.sendBookingCancelEmailToCustomer(booking);
                this.sendBookingCancelEmailToProvider(booking);
            }

        }
        return  "Done";
    }

    public  void sendBookingCancelEmailWithChargesToCustomer(Booking booking,CancelBookingModel cancelBookingModel){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            Context context = new Context();
            BookingModel bookingModel = BookingModel.fromEntity(booking);
            context.setVariable("booking", bookingModel);
            context.setVariable("data",cancelBookingModel);


            String htmlContent = templateEngine.process("cancel-booking-charges-template", context);

            helper.setTo(bookingModel.getUser().getEmail());
            helper.setSubject("Booking Cancel");
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {

        }
    }

    public  void sendBookingCancelEmailWithChargesToProvider(Booking booking,CancelBookingModel cancelBookingModel){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            Context context = new Context();
            BookingModel bookingModel = BookingModel.fromEntity(booking);
            context.setVariable("booking", bookingModel);
            context.setVariable("data",cancelBookingModel);


            String htmlContent = templateEngine.process("cancel-booking-charges-tp-template", context);

            helper.setTo(bookingModel.getTruckProvider().getEmail());
            helper.setSubject("Booking Cancel");
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {

        }
    }

    public  void sendBookingCancelEmailToCustomer(Booking booking){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            Context context = new Context();
            BookingModel bookingModel = BookingModel.fromEntity(booking);
            context.setVariable("booking", bookingModel);


            String htmlContent = templateEngine.process("cancel-booking-template", context);

            helper.setTo(bookingModel.getUser().getEmail());
            helper.setSubject("Booking Cancel");
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {

        }
    }

    public  void sendBookingCancelEmailToProvider(Booking booking){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            Context context = new Context();
            BookingModel bookingModel = BookingModel.fromEntity(booking);
            context.setVariable("booking", bookingModel);


            String htmlContent = templateEngine.process("cancel-booking-tp-template", context);

            helper.setTo(bookingModel.getTruckProvider().getEmail());
            helper.setSubject("Booking Cancel From Customer");
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {

        }
    }



    public void sendBookingConfirmEmailToTruckProvider(Booking booking){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            Context context = new Context();
            BookingModel bookingModel = BookingModel.fromEntity(booking);
            context.setVariable("booking", bookingModel);


            String htmlContent = templateEngine.process("payment-email-template", context);

            helper.setTo(bookingModel.getTruckProvider().getEmail());
            helper.setSubject("Booking Confirmation From Customer");
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {

        }
    }

    public void sendBookingPlacedEmailToTruckProvider(Booking booking){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            Context context = new Context();
            BookingModel bookingModel = BookingModel.fromEntity(booking);
            bookingModel.setStartDestination(this.getLabelFromDestination(bookingModel.getStartDestination()));
            bookingModel.setEndDestination(this.getLabelFromDestination(bookingModel.getEndDestination()));
            context.setVariable("booking", bookingModel);


            String htmlContent = templateEngine.process("booking-place-template-tp", context);

            helper.setTo(bookingModel.getTruckProvider().getEmail());
            helper.setSubject("Booking Placed By Customer");
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {

        }
    }

    public void sendBookingPlacedEmailToCustomer(Booking booking){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            Context context = new Context();
            BookingModel bookingModel = BookingModel.fromEntity(booking);
            bookingModel.setStartDestination(this.getLabelFromDestination(bookingModel.getStartDestination()));
            bookingModel.setEndDestination(this.getLabelFromDestination(bookingModel.getEndDestination()));
            context.setVariable("booking", bookingModel);


            String htmlContent = templateEngine.process("booking-place-template", context);

            helper.setTo(bookingModel.getUser().getEmail());
            helper.setSubject("Booking Placed");
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {

        }
    }

    private String getLabelFromDestination(String destination) {


        ObjectMapper objectMapper = new ObjectMapper();
        try {

            JsonNode jsonNode = objectMapper.readTree(destination);
            return jsonNode.get("label").asText();
        } catch (Exception e) {
            return "";
        }
    }
}
