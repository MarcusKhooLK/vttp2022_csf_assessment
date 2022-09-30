import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Order, OrderSummary } from '../models';
import { PizzaService } from '../pizza.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  email:string = ""
  orders: OrderSummary[] = []

  constructor(private activatedRoute: ActivatedRoute, private pizzaSvc: PizzaService) { }

  ngOnInit(): void {
    this.email = this.activatedRoute.snapshot.params['email']
    this.pizzaSvc.getOrders(this.email)
    .then(result=> {
      this.orders = result
    })
    .catch(error=>{
      console.error(">>> error: ", error)
    })
  }

}
