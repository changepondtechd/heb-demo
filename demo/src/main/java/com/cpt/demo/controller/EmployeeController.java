package com.cpt.demo.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.demo.aspect.EnablePerfLog;
import com.cpt.demo.dto.EmployeeRecord;


@RestController
@RequestMapping("/employees")
@EnablePerfLog
public class EmployeeController {

	private final Map<Long, EmployeeRecord> repo = new ConcurrentHashMap<>();
	private final AtomicLong idGen = new AtomicLong(1);

	@GetMapping
	public List<EmployeeRecord> list() {
		return new ArrayList<>(repo.values());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeRecord> get(@PathVariable Long id) {
		var e = repo.get(id);
		if (e == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(e);
	}

	@PostMapping
	public ResponseEntity<EmployeeRecord> create(@RequestBody EmployeeRecord input) {
		long id = idGen.getAndIncrement();
		var created = new EmployeeRecord(id, input.getFirstName(), input.getLastName(), input.getEmail(), input.getPhoneNumber());
		repo.put(id, created);
		return ResponseEntity.created(URI.create("/employees/" + id)).body(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmployeeRecord> update(@PathVariable Long id, @RequestBody EmployeeRecord input) {
		if (!repo.containsKey(id)) return ResponseEntity.notFound().build();
		var updated = new EmployeeRecord(id, input.getFirstName(), input.getLastName(), input.getEmail(), input.getPhoneNumber());
		repo.put(id, updated);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (repo.remove(id) == null) return ResponseEntity.notFound().build();
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
