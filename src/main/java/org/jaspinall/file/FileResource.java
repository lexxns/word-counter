package org.jaspinall.file;

import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Path("/files")
public class FileResource {
    @Inject
    FileService service;

    @GET
    public Uni<List<String>> keys() {
        return service.keys();
    }

    @POST
    @Path("/{key}")
    public String create(@PathParam("key") String key, String text) {
        service.set(key, text);
        return key;
    }

    @GET
    @Path("/{key}")
    public String get(@PathParam("key") String key) {
        return loadFileDetails(key).displayFormat();
    }

    @DELETE
    @Path("/{key}")
    public Uni<Void> delete(@PathParam("key") String key) {
        return service.del(key);
    }

    private FileDetails loadFileDetails(String key) {
        String[] words = splitWords(service.get(key));
        FileDetails fileDetails = new FileDetails(key);
        fileDetails.wordCount = words.length;
        fileDetails.averageLength = averageWordLength(words);
        fileDetails.lengths = countWordsOfLength(words);
        return fileDetails;
    }

    private float averageWordLength(String[] words) {
        return Arrays.stream(words)
                .map(String::length)
                .reduce(Integer::sum)
                .map(integer -> (float) integer / words.length)
                .orElse(0f);
    }

    private Map<Integer, Long> countWordsOfLength(String[] words) {
        return Arrays.stream(words)
                .map(String::length)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private String[] splitWords(String text) {
        return text.replace(".", "")
                .replace("*", "")
                .replace("!", "")
                .replace("?", "")
                .split(" ");
    }
}

