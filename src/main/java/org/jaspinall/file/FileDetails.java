package org.jaspinall.file;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileDetails {
    public String key;
    public int wordCount;
    public float averageLength;
    public Map<Integer, Integer> lengths = new HashMap<Integer, Integer>() {{
        put(1, 0);
        put(2, 0);
        put(3, 0);
        put(4, 0);
        put(5, 0);
        put(7, 0);
        put(10, 0);
    }};
    public int max;
    public List<Integer> maxKeys;

    public FileDetails() {}

    public FileDetails(String key) {
        this.key = key;
    }

    public String displayFormat() {
        mostFrequentLengths();
        DecimalFormat averageFormat = new DecimalFormat("###.###");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println("Word count = " + wordCount);
        printWriter.println("Average word length = " + averageFormat.format(averageLength));
        lengths.keySet().forEach(key -> printWriter.println(String.format("Number of words of length %d is %d", key, lengths.get(key))));
        printWriter.println("The most frequently occurring word length is " + max + ", for word lengths of " + maxKeys.toString());
        return stringWriter.toString();
    }

    private void mostFrequentLengths() {
        this.max = Collections.max(this.lengths.values());
        this.maxKeys = this.lengths.entrySet().stream().filter(it -> it.getValue() == max).map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
