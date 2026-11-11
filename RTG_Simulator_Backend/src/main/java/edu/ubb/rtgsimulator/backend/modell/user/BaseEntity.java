package edu.ubb.rtgsimulator.backend.modell.user;

import lombok.Data;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@Data
@ToString(callSuper = true)
public abstract class BaseEntity {
    private String uuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(getUuid(), that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUuid());
    }

    public String getUuid() {
        if (uuid == null) {
            uuid= UUID.randomUUID().toString();
        }
        return uuid;
    }
}
