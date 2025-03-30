import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-modal',
  standalone: true,
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss'],
  imports: [CommonModule],
  
})
export class ModalComponent {
  @Input() isOpen = false;
  @Input() title = 'Modal Title';
  @Output() closeEvent = new EventEmitter<void>();

  close() {
    this.isOpen = false;
    this.closeEvent.emit();
  }
}
