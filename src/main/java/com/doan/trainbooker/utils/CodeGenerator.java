package com.doan.trainbooker.utils;

import java.security.SecureRandom;

public class CodeGenerator {
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int CODE_LENGTH_RESERVATIONCODE = 6;
	private static final String NUMBERS = "0123456789";
    private static final int CODE_LENGTH_TICKETCODE = 9;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateReservationCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH_RESERVATIONCODE);
        for (int i = 0; i < CODE_LENGTH_RESERVATIONCODE; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
    
    public static String generateTicketCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH_TICKETCODE);
        for (int i = 0; i < CODE_LENGTH_TICKETCODE; i++) {
            int index = RANDOM.nextInt(NUMBERS.length());
            code.append(NUMBERS.charAt(index));
        }
        return code.toString();
    }
}
