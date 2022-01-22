package mimetype.getter.getmimes.Model;

import lombok.AllArgsConstructor;
import mimetype.getter.getmimes.Types.MimeType;

@AllArgsConstructor
public abstract class MimesToString {

    protected MimeType type;

    protected String getType(){
        switch(type){
            case PDF: return "application/pdf";
            case JPG: return "image/jpeg";
            case PNG: return "image/png";
            case CSV: return "text/csv";
            case SVG: return "image/svg+xml";
        }
        throw new IllegalArgumentException("NOT SUPPORTED MIME TYPE!");
    }
}
