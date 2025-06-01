package com.gen.cinema.domain;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractBaseUUIDEntity extends AbstractBaseEntity {

    @Column(name = "secure_id")
    private UUID secureId = UUID.randomUUID();

    public UUID getSecureId() {
        return secureId;
    }

    public void setSecureId(UUID secureId) {
        this.secureId = secureId;
    }
}
