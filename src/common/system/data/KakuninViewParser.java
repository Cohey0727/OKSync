package common.system.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import common.db.SQLKeyWord;

public class KakuninViewParser {
    public static ArrayList<ArrayList<String>> divide(File _file) throws IOException {
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        InputStreamReader is = new InputStreamReader(new FileInputStream(_file), "Shift-JIS");
        BufferedReader br = new BufferedReader(is);
        String line;
        ArrayList<String> dataBlock = new ArrayList<String>();
        while ((line = br.readLine()) != null) {
            if (line.replace(",", "").length() == 0) {
                list.add(dataBlock);
                dataBlock = new ArrayList<String>();
            } else {
                dataBlock.add(line);
            }
        }
        list.add(dataBlock);
        br.close();
        return list;
    }

    public static String getTableName(String line) {
        KakuninViewType type = judgeType(line);
        if (line == null || type == null)
            return null;
        switch (type) {
        case CWS:
            String[] elements = line.replace("【", "").replace("】", "").trim().split(" ");
            for (int i = 0; i < elements.length; i++) {
                String element = elements[i].toUpperCase();
                if (SQLKeyWord.FROM.toString().equals(element) && (i == elements.length - 1
                        || !elements[i + 1].toUpperCase().equals("(" + SQLKeyWord.SELECT.toString()))) {
                    for (int j = i + 1; j < elements.length; j++) {
                        if (!elements[j].trim().equals("")) {
                            return elements[j].toUpperCase().intern();
                        }
                    }
                    break;
                }
            }
        case CJK:
            int start = line.indexOf(".") == -1 ? line.indexOf("【") + 1
                    : Math.max(line.indexOf("."), line.indexOf("【")) + 1;
            int end = line.indexOf("】");
            return line.substring(start, end).trim().toUpperCase().intern();
        default:
            return null;
        }
    }

    public static String[] getHeaders(String line) {
        return line.split(",");
    }

    private static KakuninViewType judgeType(String line) {
        try {
            return Arrays.stream(KakuninViewType.values()).filter(type -> type.isTrueOf(line)).findFirst().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    enum KakuninViewType {
        CWS((line) -> {
            return line.toUpperCase().contains(SQLKeyWord.SELECT.toString())
                    && line.toUpperCase().contains(SQLKeyWord.FROM.toString())
                    && line.contains("【") && line.contains("】");
        }),
        CJK((line) -> {
            return !line.toUpperCase().contains(SQLKeyWord.SELECT.toString())
                    && !line.toUpperCase().contains(SQLKeyWord.FROM.toString())
                    && line.contains("【") && line.contains("】");
        });
        private final Judger judger;
        KakuninViewType(Judger _judger) {
            judger = _judger;
        }
        public boolean isTrueOf(String line) {
            return judger.isTrueOf(line);
        }
        private interface Judger {
            public boolean isTrueOf(String line);
        }
    }
}
