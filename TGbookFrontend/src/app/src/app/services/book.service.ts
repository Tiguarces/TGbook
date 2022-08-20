import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Book} from '../model/Book';

@Injectable({
    providedIn: 'root'
})
export class BookService {
    private readonly api: string;

    constructor(private http: HttpClient) {
        this.api = "http://localhost:8080/book";
    }
   
    public fetchAll(): Observable<Book[]> {
        return this.http.get<Book[]>(`${this.api}/fetch/all`);
    }
}
