import { Component, OnInit } from '@angular/core';
import {ImageListService} from "./service/image-list.service";

@Component({
  selector: 'app-image-list',
  templateUrl: './image-list.component.html',
  styleUrls: ['./image-list.component.css']
})
export class ImageListComponent implements OnInit {
  links: Array<string>;
  constructor(private imageService: ImageListService) { }

  ngOnInit() {
    this.imageService.getImagesList().subscribe(data => this.links = data);
  }

  getLinks() {
    return this.links;
  }
}
