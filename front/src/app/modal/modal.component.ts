import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="modal" [class.show]="isOpen" *ngIf="isOpen">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header bg-secondary text-white">
            <h5 class="modal-title">{{ title }}</h5>
            <button type="button" class="btn-close" (click)="close()"></button>
          </div>
          <div class="modal-body">
            <ng-content></ng-content>
          </div>
        </div>
      </div>
    </div>
    <div class="modal-backdrop fade show" *ngIf="isOpen"></div>
  `,
  styles: [`
    .modal {
      display: none;
      position: fixed;
      z-index: 1055;
      left: 0;
      top: 0;
      width: 95%;
      height: 100%;
      overflow: auto;
      background-color: rgba(0,0,0,0.4);
    }
    .modal.show {
      display: block;
    }
    .modal-dialog {
      margin: 2% auto;
      width: 100%;
      max-width: 1200px;
    }
    .modal-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding-bottom: 10px;
      border-bottom: 1px solid #dee2e6;
    }
    .modal-footer {
      display: flex;
      justify-content: flex-end;
      padding-top: 10px;
      border-top: 1px solid #dee2e6;
    }
    .btn-close {
      background-color: transparent;
      border: none;
    }
    .modal-backdrop {
      position: fixed;
      top: 0;
      left: 0;
      z-index: 1040;
      width: 100vw;
      height: 100vh;
      background-color: #000;
    }
    .modal-backdrop.fade {
      opacity: 0;
    }
    .modal-backdrop.show {
      opacity: 0.5;
    }
  `]
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
