import {Injectable} from "@angular/core";
import {ToastrService} from "ngx-toastr";
import {Position} from "./Position";

@Injectable()
export class ToastrHelper {
    constructor(private toastr: ToastrService) {}

    public invokeInfo(message: string, title: string, position: Position): void {
        this.toastr.info(message, title, {
            positionClass: position.toString()
        });
    }

    public invokeWarning(message: string, title: string, position: Position): void {
        this.toastr.warning(message, title, {
            positionClass: position.toString()
        });
    }

    public invokeError(message: string, title: string, position: Position): void {
        this.toastr.error(message, title, {
            positionClass: position.toString()
        });
    }
}
