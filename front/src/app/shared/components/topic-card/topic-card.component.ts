import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatCard, MatCardContent, MatCardFooter, MatCardHeader, MatCardTitle} from "@angular/material/card";

@Component({
  selector: 'app-topic-card',
  standalone: true,
    imports: [
        MatButton,
        MatCard,
        MatCardContent,
        MatCardFooter,
        MatCardHeader,
        MatCardTitle
    ],
  templateUrl: './topic-card.component.html',
  styleUrl: './topic-card.component.scss'
})
export class TopicCardComponent {
  @Input({required: true}) title!: string;
  @Input({required: true}) description!: string;
  @Input({required: true}) btnContent!: string;
  @Input({required: true}) isSubscribed!: boolean;
  @Output() manageSubscription: EventEmitter<void> = new EventEmitter<void>();

  protected handleFn(event: Event) {
    this.manageSubscription.emit();
  }}
