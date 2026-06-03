package br.ufersa.academia.service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String hashSenha(String senhaPlana) {
        if (senhaPlana == null) {
            return null;
        }
        return BCrypt.hashpw(senhaPlana, BCrypt.gensalt(12));
    }

    public static boolean verificarSenha(String senhaPlana, String senhaHash) {
        if (senhaPlana == null || senhaHash == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(senhaPlana, senhaHash);
        } catch (Exception e) {
            return false;
        }
    }
}
