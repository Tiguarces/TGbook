import {Component, OnInit} from '@angular/core';
import {IconDefinition} from '@fortawesome/fontawesome-svg-core';
import {Book} from '../../model/Book';
import {BookService} from '../../services/book.service';
import {BookHelper} from '../../Utils/BookHelper';
import {IconHelper} from '../../Utils/IconHelper';

@Component({
    selector: 'app-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
    public books: Book[];
    public currentBook: Book;

    constructor(private service: BookService, private helper: BookHelper,
                private iconHelper: IconHelper) {
        this.books = [];
        this.currentBook = {} as Book;
        this.currentBook.images = [];

        this.service.fetchAll().subscribe({
            next:
                fetchedBooks => {
                    this.books = fetchedBooks;
                    this.shuffle();
                },
            error:
                error => console.log(error)
        });
    }

    ngOnInit(): void {
    }

    private shuffle(): void {
        setTimeout(() => {
            this.books = this.helper.shuffleArray(this.books);
            this.shuffle();
        }, 5000);
    }

    public getGitHubIcon(): IconDefinition {
        return this.iconHelper.gitHub;
    }
}
