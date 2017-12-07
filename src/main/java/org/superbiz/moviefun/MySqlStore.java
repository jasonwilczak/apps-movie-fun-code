package org.superbiz.moviefun;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
@Repository
public class MySqlStore implements BlobStore {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void put(Blob blob) throws IOException {
        entityManager.persist(new MySqlBlob(blob.name,blob.inputStream,blob.contentType));
    }

    @Override
    public Optional<Blob> get(String name) {
        try {
            TypedQuery<MySqlBlob> tq = entityManager.createQuery("from MySqlBlob WHERE name=?", MySqlBlob.class);
            MySqlBlob result = tq.setParameter(1, name).getSingleResult();
            return Optional.of(new Blob(result.getName(),result.getContent(),result.getContenttype()));
        } catch(NoResultException noresult) {
            // if there is no result
            System.out.println("No data");
            return null;
        } catch(NonUniqueResultException notUnique) {
            // if more than one result
            System.out.println("too much data");
            return null;
        }
    }

    @Override
    public void deleteAll() {

    }

}

