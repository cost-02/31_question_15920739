package com.example;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class TestRsa {
    private BigInteger p, q;
    private BigInteger n;
    private BigInteger PhiN;
    private BigInteger e, d;

    public TestRsa() {
        initialize();
    }

    public void initialize() {
        int SIZE = 512;
        // Passo 1: Seleziona due grandi numeri primi p e q.
        p = new BigInteger(SIZE, 15, new Random());
        q = new BigInteger(SIZE, 15, new Random());

        // Passo 2: Calcola n = p * q
        n = p.multiply(q);

        // Passo 3: Calcola ø(n) = (p - 1) * (q - 1)
        PhiN = p.subtract(BigInteger.valueOf(1));
        PhiN = PhiN.multiply(q.subtract(BigInteger.valueOf(1)));

        // Passo 4: Trova e tale che gcd(e, ø(n)) = 1 e 1 < e < ø(n)
        do {
            e = new BigInteger(2 * SIZE, new Random());
        } while ((e.compareTo(PhiN) != 1) || (e.gcd(PhiN).compareTo(BigInteger.valueOf(1)) != 0));

        // Passo 5: Calcola d tale che e * d = 1 (mod ø(n))
        d = e.modInverse(PhiN);
    }

    public BigInteger encrypt(BigInteger plaintext) {
        return plaintext.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger ciphertext) {
        return ciphertext.modPow(d, n);
    }

    public static void main(String[] args) throws IOException {
        TestRsa app = new TestRsa();
        int plaintext;
        System.out.println("Inserisci un carattere: ");
        plaintext = System.in.read();
        BigInteger bplaintext, bciphertext;

        bplaintext = BigInteger.valueOf((long) plaintext);
        bciphertext = app.encrypt(bplaintext);
        System.out.println("Testo in chiaro: " + bplaintext.toString());
        System.out.println("Testo cifrato: " + bciphertext.toString());

        bplaintext = app.decrypt(bciphertext);
        System.out.println("Dopo la decifratura Testo in chiaro: " + (char) bplaintext.intValue());
    }
}
