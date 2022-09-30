// Implement the methods in PizzaService for Task 3
// Add appropriate parameter and return type 

import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { Order, OrderSummary } from "./models";

@Injectable()
export class PizzaService {

  constructor(private httpClient: HttpClient) { }

  // POST /api/order
  // Add any required parameters or return type
  createOrder(order: Order) : Promise<any> {
    const httpHeaders = new HttpHeaders()
                  .set("Content-Type", "application/json")
                  .set("Accept", "application/json")
    return firstValueFrom(
        this.httpClient.post<any>("/api/order", order, {headers:httpHeaders})
    )
  }

  // GET /api/order/<email>/all
  // Add any required parameters or return type
  getOrders(email:string) : Promise<OrderSummary[]> {
    return firstValueFrom(
      this.httpClient.get<OrderSummary[]>(`/api/order/${email}/all`)
    )
  }

}
