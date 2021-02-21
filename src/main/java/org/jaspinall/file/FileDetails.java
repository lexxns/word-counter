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
    public Map<Integer, Long> lengths = new HashMap<>();
    public long max;
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
        lengths.keySet().forEach(lengthKey -> printWriter.println(String.format("Number of words of length %d is %d", lengthKey, lengths.get(lengthKey))));
        printWriter.println(String.format("The most frequently occurring word length is %d, for word lengths of %s", max, maxKeysString()));
        return stringWriter.toString();
    }

    private void mostFrequentLengths() {
        this.max = Collections.max(this.lengths.values());
        this.maxKeys = this.lengths.entrySet().stream().filter(it -> it.getValue() == max).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    private String maxKeysString() {
        String maxKeysString = maxKeys.stream().map(String::valueOf).collect(Collectors.joining(", "));
        int index = maxKeysString.lastIndexOf(", ");
        if ( index >= 0)
            maxKeysString = new StringBuilder(maxKeysString).replace(index, index+1, " &").toString();
        return maxKeysString;
    }
}
