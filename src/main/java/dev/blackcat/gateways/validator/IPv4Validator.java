package dev.blackcat.gateways.validator;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class IPv4Validator {

    private static final String IPv4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";
    private static final Pattern IPv4_PATTERN = Pattern.compile(IPv4_REGEX);

    public boolean isValid(String ip) {
        if (ip == null)
            return false;

        if (!IPv4_PATTERN.matcher(ip).matches())
            return false;

        String[] parts = ip.split("\\.");
        try {
            for (String segment: parts) {
                if (Integer.parseInt(segment) > 255 || (segment.length() > 1 && segment.startsWith("0"))) {
                    return false;
                }
            }
        }
        catch(NumberFormatException e) {
            return false;
        }

        return true;
    }

}
