package mimetype.getter.getmimes.Model;

import lombok.extern.slf4j.Slf4j;
import mimetype.getter.getmimes.Types.MimeType;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class DonwloadMimes extends MimesToString{
    private static final Tika TIKA = new Tika();
    private static MimeTypes MIME_TYPES = MimeTypes.getDefaultMimeTypes();

    private final String url;
    private final Path download;

    public DonwloadMimes(MimeType type, String url, Path download) {
        super(type);
        this.url = url;
        this.download = download;
    }


    public void processFiles() throws IOException {

        var elements = MakeConnection(this.url).orElseThrow();

        List<String> collect = elements.stream().map(e -> {
            return e.attr("abs:src");
        }).filter(el -> !el.isEmpty()).collect(Collectors.toList());

        collect.forEach(url -> {
            try {
                var link = new URL(url);
                final String detect = TIKA.detect(link);
                URLConnection urc = link.openConnection();
                urc.connect();
                String mime = getType();
                if(detect.equals(mime)){
                    byte[] bytes = urc.getInputStream().readAllBytes();
                    var mimeType = MIME_TYPES.forName(mime);
                    var ext = mimeType.getExtension();
                    Path imagePath = download.resolve(UUID.randomUUID().toString().concat(ext));
                    try(OutputStream writer = Files.newOutputStream(imagePath);){
                        writer.write(bytes);
                    }
                    catch(Exception e){
                        log.warn("Error writing in file");
                    }
                }
                else{
                    log.warn("mime Type {} not detected on url {}", mime, url);
                }
            } catch (IOException | MimeTypeException e) {
                log.warn("Ecxeption retrieving data",e);
            }
        });

    }

    private Optional<Elements> MakeConnection(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
            log.info("Made connection with {}", document.title());
            var elements = document.select("img[src]");
            return Optional.of(elements);
        } catch (IOException e) {
            log.warn("Couldn't make connection with {}", url, e);
        }
        return Optional.empty();
    }
}
