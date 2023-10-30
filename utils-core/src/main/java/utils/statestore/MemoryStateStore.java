/**
 * The Initial Developer of some parts of this file, which are copied from, derived from, or
 * inspired by Cognite Java SDK for CDF, is Cognite AS (http://www.cognite.com/).
 * Copyright 2022 Cogntite AS. All Rights Reserved.
 *
 * The original code has been changed to use Objects.requireNonNull() instead of the Google Preconditions
 * library.
 */
package utils.statestore;

import com.google.auto.value.AutoValue;

import java.time.Duration;

/**
 * A state store in-memory only. Not backed by any persisted storage.
 *
 * {@inheritDoc}
 */
@AutoValue
public abstract class MemoryStateStore extends AbstractStateStore {

    private static Builder builder() {
        return new AutoValue_MemoryStateStore.Builder()
                .setMaxCommitInterval(DEFAULT_MAX_COMMIT_INTERVAL);
    }

    /**
     * Initialize an in-memory state store.
     *
     * @return the state store.
     */
    public static MemoryStateStore create() {
        return MemoryStateStore.builder()
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() throws Exception {
        LOG.info("load() - calling load() has no effect for the in-memory state store.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void commit() throws Exception {
        LOG.info("commit() - calling commit() has no effect for the in-memory state store.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean start() {
        String logPrefix = "start() - ";
        LOG.info(logPrefix + "calling start() has no effect for the in-memory state store.");
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean stop() {
        String logPrefix = "stop() -";
        LOG.info(logPrefix + "calling stop() has no effect for the in-memory state store.");

        return false;
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setMaxCommitInterval(Duration value);
        abstract MemoryStateStore build();
    }
}
