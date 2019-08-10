import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ImageListService {
  imagesUrl = 'api/images/all';

  constructor(private http: HttpClient) { }

  getImagesList() {
    return this.http.get<string[]>(this.imagesUrl);
  }
}
