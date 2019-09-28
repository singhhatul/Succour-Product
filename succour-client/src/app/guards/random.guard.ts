import { Injectable } from '@angular/core';
import { CanActivate, CanLoad, Router } from '@angular/router';
import { AuthService } from '../authServices/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RandomGuard implements CanActivate, CanLoad {

  constructor(private authService: AuthService, private router: Router) { }

  canActivate() {
    return this.canLoad();
  }

  canLoad() {
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['../dash-testing']);
    }
    return this.authService.isLoggedIn();
  }

}
