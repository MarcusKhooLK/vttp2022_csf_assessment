import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Order } from '../models';
import { PizzaService } from '../pizza.service';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PizzaToppings: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  pizzaSize = SIZES[0]

  form!: FormGroup
  toppingsArray!: FormArray
  
  constructor(private fb: FormBuilder, private router: Router, private pizzaSvc: PizzaService) {}

  ngOnInit(): void {
    this.form = this.createForm();
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

  onCheckboxChange(e: any) {
    const checkArray: FormArray = this.form.get('toppings') as FormArray;
    if (e.target.checked) {
      checkArray.push(new FormControl(e.target.value));
    } else {
      let i: number = 0;
      checkArray.controls.forEach(item => {
        if (item.value == e.target.value) {
          checkArray.removeAt(i);
          return;
        }
        i++;
      });
    }
  }

  private createForm() : FormGroup {
    this.toppingsArray = this.fb.array([], [Validators.required]);
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      email: this.fb.control<string>('', [Validators.required, Validators.email]),
      size: this.fb.control<number>(0, [Validators.required, Validators.min(0), Validators.max(3)]),
      base: this.fb.control<string>('thin', [Validators.required]),
      sauce: this.fb.control<string>('classic', [Validators.required]),
      comments: this.fb.control<string>(''),
      toppings: this.toppingsArray
    })
  }

  onSubmitOrder() {
    const order: Order = this.form.value as Order
    order.base = this.form.get('base')?.value === 'thin' ? false : true
    this.pizzaSvc.createOrder(order)
    .then(result=> {
      this.router.navigate(["/orders", this.form.get('email')?.value])
    })
    .catch(error=>{
      console.error(">>> error: ", error)
    })
  }

  onListOrder() {
    this.router.navigate(["/orders", this.form.get('email')?.value])
  }

}
