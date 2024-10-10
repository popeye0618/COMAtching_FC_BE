package comatchingfc.comatchingfc.utils.uuid;

import com.fasterxml.uuid.Generators;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDUtil {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    //UUID 생성 후 binary 변환
    public static byte[] createUUID() {
        UUID uuidV1 = Generators.timeBasedGenerator().generate();
        String[] uuidV1Parts = uuidV1.toString().split("-");
        String sequentialUUID = uuidV1Parts[2]+uuidV1Parts[1]+uuidV1Parts[0]+uuidV1Parts[3]+uuidV1Parts[4];

        return uuidStringToBytes(sequentialUUID);
    }

    public static byte[] uuidStringToBytes(String sequentialUUID) {
        String sequentialUuidV1 = String.join("", sequentialUUID);
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(Long.parseUnsignedLong(sequentialUuidV1.substring(0, 16), 16));
        bb.putLong(Long.parseUnsignedLong(sequentialUuidV1.substring(16), 16));
        return bb.array();
    }

    //사람이 식별 가능한 문자열로 변환
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars).toLowerCase();
    }

    public static String bytesToStringLiteral(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02x", bytes[i])); // 바이트 값을 16진수 소문자로 변환

            // UUID 포맷에 따라 특정 위치에 "-" 추가
            if (i == 3 || i == 5 || i == 7 || i == 9) {
                sb.append("-");
            }
        }
        return sb.toString();
    }

    public static byte[] stringToByteLiteral(String string) {
        // "-"를 제거한 후 "0x"가 없는 16진수 문자열만 남김
        string = string.replace("-", "");

        int len = string.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) Integer.parseInt(string.substring(i, i + 2), 16); // 16진수로 변환하여 바이트로 저장
        }
        return data;
    }


}
