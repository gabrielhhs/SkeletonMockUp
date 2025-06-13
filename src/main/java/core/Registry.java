package core;

import java.util.*;

public class Registry<T extends Registerable> {
	private final Map<String, T> data = new HashMap<>();

	public Optional<T> get(String id) {
		return Optional.ofNullable(this.data.get(id));
	}
	public Collection<T> all() {
		return this.data.values();
	}

	public Registry<T> register(T object) {
		this.data.put(object.getId(), object);
		return this;
	}
	public Registry<T> register(Collection<T> objects) {
		for (T object : objects) {
			this.register(object);
		}
		return this;
	}
	public Registry<T> register(T... objects) {
		return this.register(Set.of(objects));
	}
}