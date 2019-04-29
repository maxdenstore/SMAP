//inspired by https://stackoverflow.com/questions/43055661/reading-csv-file-in-android-app

package com.example.maxbarlyjorgensen_au520670_f19smap_assignment2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVinput {
    InputStream inputStream;

    public CSVinput(InputStream is) {
        this.inputStream = is;
    }

    public List<String[]> read() {
        List<String[]> res = new ArrayList<String[]>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(";");
                res.add(row);
            }
        } catch (IOException exp) {
            throw new RuntimeException("Err reading csv" + exp);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Err closing csv" + e);
            }
        }

        return res;
    }


}
