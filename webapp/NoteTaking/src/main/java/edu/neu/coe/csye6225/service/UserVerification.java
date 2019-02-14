package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.User;

import java.util.Base64;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class UserVerification {
    public static User addVerification(String auth) {
        if (StringUtils.isBlank(auth) || StringUtils.isEmpty(auth))
            return null;
        String basic = "Basic";
        if (!auth.contains(basic))
            return null;
        List<String> list;
        String codeString = auth.substring(basic.length()).trim();
        try {
            codeString = new String(Base64.getDecoder().decode(codeString));
            list = java.util.Arrays.asList(codeString.replace(" ", "").split(":"));
        } catch (Exception e) {
            return null;
        }
        if (list.size() != 2) {
            return null;
        }

        User user = new User();
        user.setUsername(list.get(0));
        user.setPassword(list.get(1));
        return user;
    }
}
