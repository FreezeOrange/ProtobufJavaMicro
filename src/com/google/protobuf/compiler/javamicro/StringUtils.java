package com.google.protobuf.compiler.javamicro;

/**
 * Created with IntelliJ IDEA.
 * Date: 2014/3/27
 */
public class StringUtils {

    public static String cHexEscape(String src) {
        char[] srcArr = src.toCharArray();
        int destLength = srcArr.length * 4;
        char[] dest = new char[destLength];
        int len = cEscapeInternal(srcArr, dest, true, false);
        if (len > 0) {
            return new String(dest, 0, len);
        }

        throw new IllegalStateException();
    }

    public static String utf8SafeCEscape(String src) {
        char[] srcArr = src.toCharArray();
        int destLength = srcArr.length * 4;
        char[] dest = new char[destLength];
        int len = cEscapeInternal(srcArr, dest, false, true);
        if (len > 0) {
            return new String(dest, 0, len);
        }

        throw new IllegalStateException();
    }

    public static String cEscape(String src) {
        char[] srcArr = src.toCharArray();
        int destLength = srcArr.length * 4;
        char[] dest = new char[destLength];
        int len = cEscapeInternal(srcArr, dest, false, false);
        if (len > 0) {
            return new String(dest, 0, len);
        }

        throw new IllegalStateException();
    }

    /**
     * cEscapeString()
     * cHexEscapeString()
     * Copies 'src' to 'dest', escaping dangerous characters using
     * C-style escape sequences. This is very useful for preparing query
     * flags. 'src' and 'dest' should not overlap. The 'Hex' version uses
     * hexadecimal rather than octal sequences.
     * Returns the number of bytes written to 'dest' (not including the \0)
     * or -1 if there was insufficient space.
     * <p/>
     * Currently only \n, \r, \t, ", ', \ and !isprint() chars are escaped.
     */
    private static int cEscapeInternal(
            char[] src, int srcOffset, int srcCount,
            char[] dest, int destOffset, int destCount,
            boolean useHex, boolean utf8Safe
    ) {
        int used = destOffset;
        boolean last_hex_escape = false; // true if last output char was \xNN
        int len = srcOffset + srcCount;
        for (int i = srcOffset; i < len; ++i) {
            if (destCount - used < 1) return -1; // Need space for at least one letter escape

            boolean is_hex_escape = false;
            switch (src[i]) {
                case '\n': dest[used++] = '\\'; dest[used++] = 'n';  break;
                case '\r': dest[used++] = '\\'; dest[used++] = 'r';  break;
                case '\t': dest[used++] = '\\'; dest[used++] = 't';  break;
                case '\"': dest[used++] = '\\'; dest[used++] = '\"'; break;
                case '\'': dest[used++] = '\\'; dest[used++] = '\''; break;
                case '\\': dest[used++] = '\\'; dest[used++] = '\\'; break;
                default:
                    // Note that if we emit \xNN and the src character after that is a hex
                    // digit then that digit must be escaped too to prevent it being
                    // interpreted as part of the character code by C.
                if ((!utf8Safe || src[i] < 0x80) && (Character.isWhitespace(src[i])
                            || (last_hex_escape && isxdigit(src[i])))) {
                    if (destCount - used < 4) // need space for 4 letter escape
                        return -1;
                    sprintf(dest, used, useHex,  src[i]);
//                    sprintf(dest + used, (useHex ? "\\x%02x" : "\\%03o"), src[i]);
                    is_hex_escape = useHex;
                    used += 4;
                } else {
                    dest[used++] = src[i]; break;
                }
            }
            last_hex_escape = is_hex_escape;
        }

//        if (destCount - used < 1)   // make sure that there is room for \0
//            return -1;
//
//        dest[used] = '\0';   // doesn't count towards return value though
        return used;
    }

    private static void sprintf(char[] dest, int used, boolean useHex, char c) {
        dest[used++] = '\\';
        if (useHex) {
            dest[used++] = 'x';
            dest[used++] = (char) ((c & 0xF0) >> 4 + '0');
            dest[used] = (char) ((c & 0xF) + '0');
        } else {
            dest[used++] = (char) ((c & 0x1C) >> 6 + '0');
            dest[used++] = (char) ((c & 0x38) >> 3 + '0');
            dest[used] = (char) ((c & 0x7) + '0');
        }
    }

    private static boolean isxdigit(char c) {
        c = Character.toLowerCase(c);
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f');
    }

    /**
     * cEscapeString()
     * cHexEscapeString()
     * Copies 'src' to 'dest', escaping dangerous characters using
     * C-style escape sequences. This is very useful for preparing query
     * flags. 'src' and 'dest' should not overlap. The 'Hex' version uses
     * hexadecimal rather than octal sequences.
     * Returns the number of bytes written to 'dest' (not including the \0)
     * or -1 if there was insufficient space.
     * <p/>
     * Currently only \n, \r, \t, ", ', \ and !isprint() chars are escaped.
     */
    private static int cEscapeInternal(char[] src, char[] dest, boolean useHex, boolean utf8Safe) {
        return cEscapeInternal(src, 0, src.length, dest, 0, dest.length, useHex, utf8Safe);
    }

    public static boolean isAllAscii(String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] & 0x80) != 0) {
                return false;
            }
        }
        return true;
    }
}
