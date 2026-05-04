import { Component, output, signal, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-photo-upload',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatButtonModule],
  templateUrl: './photo-upload.component.html',
  styleUrls: ['./photo-upload.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PhotoUploadComponent {
  photoSelected = output<File>();
  photoRemoved = output<void>();

  previewUrl = signal<string | null>(null);
  fileName = signal<string | null>(null);

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];
      this.fileName.set(file.name);
      
      const reader = new FileReader();
      reader.onload = (e) => {
        this.previewUrl.set(e.target?.result as string);
        this.photoSelected.emit(file);
      };
      reader.readAsDataURL(file);
    }
  }

  removePhoto(): void {
    this.previewUrl.set(null);
    this.fileName.set(null);
    this.photoRemoved.emit();
  }
}
