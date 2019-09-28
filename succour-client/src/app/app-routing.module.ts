import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { PieChartComponent } from './components/pie-chart/pie-chart.component';
import { BarChartComponent } from './components/bar-chart/bar-chart.component';
import { RealTimeLineChartComponent } from './components/real-time-line-chart/real-time-line-chart.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { MultiLineChartComponent } from './components/multi-line-chart/multi-line-chart.component';
import { AuthGuard } from './guards/auth.guard';
import { RandomGuard } from './guards/random.guard';
import { DashTestingComponent } from './dash-testing/dash-testing.component';
import { FinanceDashboardComponent } from './components/finance-dashboard/finance-dashboard.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [AuthGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [RandomGuard] },
  { path: 'finance', component: FinanceDashboardComponent, canActivate: [RandomGuard] },
  { path: 'home', component: DashTestingComponent },
  { path: '**', redirectTo: 'home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})

export class AppRoutingModule { }
