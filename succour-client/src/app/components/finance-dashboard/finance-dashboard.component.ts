import { Component, OnInit } from '@angular/core';
import { FinanceSocketService } from '../../services/finance-socket.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-finance-dashboard',
  templateUrl: './finance-dashboard.component.html',
  styleUrls: ['./finance-dashboard.component.scss']
})
export class FinanceDashboardComponent implements OnInit {
  // [x: string]: { label: string; value: number; }[];
  private transactions: any[];

  constructor(private webSocket: FinanceSocketService, private router: Router) { }
  ngOnInit() {
    this.transactions = [];
    this.webSocket.connect();
    this.webSocket.getMessage().subscribe((financeTransactionString: string) => {
      let financeTransaction = JSON.parse(financeTransactionString);
      this.transactions.push(financeTransaction);
    });

  }

  logout() {
    localStorage.removeItem('JWT_TOKEN');
    this.router.navigate(['../dash-testing']);
  }

}
