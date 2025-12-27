package com.litri.strato.csm.service;

import com.litri.strato.dsm.Info;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

// TODO: All here is subject to change.
@Slf4j
public class InfoService<T> {

	protected final long timeout;
	
	// TODO: Default is not cluster-safe if sticky session not used.
	protected final Map<UUID, Info<T>> infos;
	
	public InfoService() {
		this(Long.MAX_VALUE);
	}
	
	public InfoService(long timeout) {
		this.timeout = timeout;
		this.infos = ExpiringMap.builder()
			.expiration(this.timeout, TimeUnit.SECONDS)
			.expirationPolicy(ExpirationPolicy.ACCESSED)
			.asyncExpirationListener((id, info) -> log.debug("Expiring info {}", info))
			.entryLoader(id -> Info.<T>builder().id((UUID) id).build())
			.build();
	}

	public Collection<Info<T>> getAll() {
		return this.infos.values();
	}
	
	public Info<T> get(UUID id) {
		return this.infos.get(id);
	}
	
	public Info<T> init(T detail) {
		Info<T> info = this.infos.get(UUID.randomUUID());
		info.setDetail(detail);
		info.setInitialDate();
		return info;
	}
	
	public Info<T> update(UUID id, T detail) {
		Info<T> info = this.infos.get(id);
		info.setDetail(detail);
		info.setLastDate();
		return info;
	}
	
	public Info<T> fini(UUID id) {
		Info<T> info = this.infos.get(id);
		info.setFinalDate();
		return info;
	}
	
	public Info<T> remove(UUID id) {
		return this.infos.remove(id);
	}
	
	public void removeAll() {
		this.infos.clear();
	}
	
}
