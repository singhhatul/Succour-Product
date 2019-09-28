import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../authServices/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  decode: any;
  constructor(private authService: AuthService, private router: Router) { }

  canActivate() {
    if (this.authService.isLoggedIn() && this.authService.getUserRole() !== 'finance') {
      this.router.navigate(['/dashboard']);
    } else if (this.authService.isLoggedIn() && this.authService.getUserRole() === 'finance') {
      this.router.navigate(['/finance']);
    } else {
      return !this.authService.isLoggedIn();
    }
  }

  decodeMethod() {

  }
}
