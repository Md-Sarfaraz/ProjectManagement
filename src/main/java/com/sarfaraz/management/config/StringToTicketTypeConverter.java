package com.sarfaraz.management.config;


import com.sarfaraz.management.exception.UserInputError;
import com.sarfaraz.management.repository.TicketRepo;
import org.springframework.core.convert.converter.Converter;

//public class StringToTicketTypeConverter implements Converter<String, TicketType> {
//
//    @Override
//    public TicketType convert(String source) {
//        if (!source.isBlank()) {
//            TicketRepo.Types types = TicketRepo.Types.valueOf(source);
//            return new TicketType(types.name());
//        } else throw new UserInputError("Ticket Type Can't Be EMPTY");
//
//    }
//}
