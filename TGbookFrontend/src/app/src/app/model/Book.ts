import { Review } from "./Review";
import { Image } from "./Image";

export interface Book {
    name: string,
    authors: string[],
    reviews: Review[],
    publisher: string,
    category: {
        name: string,
        subCategoryName: string
    },
    type: string,
    dimensions: string,
    numberOfPages: number,
    amount: number,
    price: number,
    images: Image[],
    description: string;
}
