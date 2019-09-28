import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthServiceService } from './auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class AuthguardGuard implements  CanActivate{
  constructor(private auth: AuthServiceService, private router: Router) {}
  canActivate() {
    if (!this.auth.isLoggedIn()) {
      this.router.navigate(['/login']);
    }
    return !this.auth.isLoggedIn();
  }
}
