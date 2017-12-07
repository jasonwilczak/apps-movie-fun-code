package org.superbiz.moviefun;
import org.apache.tika.io.IOUtils;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

@Entity
public class MySqlBlob implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length=1000000)
    private byte[] content;

    private String contenttype;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public MySqlBlob() {
    }

    public MySqlBlob(String name, InputStream contentStream, String contenttype) throws IOException {
        this.name=name;
        this.contenttype = contenttype;
        this.content = IOUtils.toByteArray(contentStream);
    }
}
