package net.catchpole.silicone.lang;

//   Copyright 2014 catchpole.net
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

public final class Strings {
    private Strings() {
    }

    /**
     * Converts a String into a String array by splitting it on a specified character token.
     * <p/>
     * <p>This method operates differently to StringTokenizer or String.split in that it returns empty records for
     * consecutive tokens. Useful in cases such as parsing comma seperated values where two consecutive commas
     * represents an empty record.
     * <p/>
     * <p>eg.  Splitting commas on <pre>This,, , is the,, way</pre> returns <pre>["This","","","is the","","way"]</pre>
     *
     * @param string
     * @param separator
     */
    public static String[] tokenize(String string, char separator) {
        String[] words = new String[string == null ? 0 : instancesOf(string, separator) + 1];

        if (words.length != 0) {
            int x = 0;
            int start = 0;
            int end;

            while ((end = string.indexOf(separator, start)) != -1) {
                words[x++] = (start == end ? "" : string.substring(start, end).trim());
                start = end + 1;
            }

            words[x] = !(start != string.length()) ? "" : string.substring(start).trim();
        }

        if (words.length == 1 && words[0].length() == 0) {
            return new String[0];
        }

        return words;
    }

    public static int instancesOf(String string, char c) {
        if (string == null) {
            throw new NullPointerException();
        }
        int l = string.length();
        int inst = 0;

        for (int x = 0; x < l; x++) {
            if (string.charAt(x) == c) {
                inst++;
            }
        }

        return inst;
    }
}
