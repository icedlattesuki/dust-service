package com.dust.util;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Web3Utils {

    public static String digest(String message) {
        message = "\u0019Ethereum Signed Message:\n" + message.length() + message;
        Keccak.Digest256 digest256 = new Keccak.Digest256();
        byte[] bytes = digest256.digest(message.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(bytes));
    }

    public static String recoverAddress(String digest, String signature) {
        Sign.SignatureData signatureData = Web3Utils.getSignatureData(signature);
        int header = 0;
        for (byte b : signatureData.getV()) {
            header = (header << 8) + (b & 0xFF);
        }
        if (header < 27 || header > 34) {
            return null;
        }
        int recId = header - 27;
        BigInteger key = Sign.recoverFromSignature(recId,
                new ECDSASignature(new BigInteger(1, signatureData.getR()), new BigInteger(1, signatureData.getS())),
                Numeric.hexStringToByteArray(digest));
        if (key == null) {
            return null;
        }
        return ("0x" + Keys.getAddress(key)).trim();
    }

    private static Sign.SignatureData getSignatureData(String signature) {
        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }
        byte[] r = (byte[]) Arrays.copyOfRange(signatureBytes, 0, 32);
        byte[] s = (byte[]) Arrays.copyOfRange(signatureBytes, 32, 64);
        return new Sign.SignatureData(v, r, s);
    }
}
