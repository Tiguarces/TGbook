package pl.Tiguarces.TGbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.Tiguarces.TGbook.model.book.request.SaveRequest;
import pl.Tiguarces.TGbook.service.BookService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/book")
public class BookController {
    private final BookService service;

    @PostMapping(path = "/save")
    public ResponseEntity<Object> save(@RequestBody @Valid SaveRequest request) {
        service.save(request);
        return ResponseEntity
                    .status(CREATED)
                .build();
    }

    @GetMapping(path = "/fetch/all")
    public ResponseEntity<Object> fetchAll() {
        return ResponseEntity
                .status(OK)
                .body(service.fetchAll());
    }
}
