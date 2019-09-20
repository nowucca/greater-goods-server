# Example errors   

Here are some example errors and fixes I found during this module.
            
## Extra characters in the page template
If I have an expression

````
 <input class="textField" type="text"
                               size="20" maxlength="45"
                               id="name" name="name"
                               v-on:blur="performChecks",
                               v-model="customerForm.name">
````

I got a blank page.

It was because of the extra comma on the second last line.

The error was:

````                               
Uncaught DOMException: Failed to execute 'setAttribute' on 'Element': ',' is not a valid attribute name.
    at baseSetAttr (http://localhost:8080/GreaterGoodsValidate/js/libs/vue.js:6772:10)
    at setAttr (http://localhost:8080/GreaterGoodsValidate/js/libs/vue.js:6747:7)
    at Array.updateAttrs (http://localhost:8080/GreaterGoodsValidate/js/libs/vue.js:6702:9)
    at invokeCreateHooks (http://localhost:8080/GreaterGoodsValidate/js/libs/vue.js:6058:24)
    at createElm (http://localhost:8080/GreaterGoodsValidate/js/libs/vue.js:5945:13)
    at createChildren (http://localhost:8080/GreaterGoodsValidate/js/libs/vue.js:6042:11)
    at createElm (http://localhost:8080/GreaterGoodsValidate/js/libs/vue.js:5943:11)
    at createChildren (http://localhost:8080/GreaterGoodsValidate/js/libs/vue.js:6042:11)
    at createElm (http://localhost:8080/GreaterGoodsValidate/js/libs/vue.js:5943:11)
    at createChildren (http://localhost:8080/GreaterGoodsValidate/js/libs/vue.js:6042:11)
````

## ReferenceError
Error:
````
ReferenceError: elementId is not defined
    at Vue.blurred (CheckoutPage.js:65)
    at invokeWithErrorHandling (vue.js:1863)
    at HTMLInputElement.invoker (vue.js:2188)
    at HTMLInputElement.original._wrapper (vue.js:7541)
````
    
Cause:
````
blurred: function(evt) {
    console.log("Blur method called on element "+evt.target.id);
    this.clearErrors(elementId);
    this.performChecks();
},
````

I was referring to a parameter I had eliminated (elementId).

Fix: 
Stop using the name that is gone, or clean it up.
````
blurred: function(evt) {
    let elementId = evt.target.id;
    console.log("Blur method called on element "+elementId);
    this.clearErrors(elementId);
    this.performChecks();
},
````

## Repeated Errors strings with blur events
We are displaying growing lists of form error messages with blur handling.
````
Phone required.
Email required.
Phone required.
Email required.
Phone required.
Email required.
Phone required.
Email required.
Phone required.
Email required.
Phone required.
Email required.
Phone required.
Email required.
Address required.
Phone required.
Email required.
Names should be between 200 and 300 characters long.
Address required.
Phone required.
Email required.
````

Cause: We need to realize that EVERY time the errors list changes, we will recompute the unordered error list and add to it.
````
clearErrors: function(fieldName = "") {
    if (!fieldName) {
        this.errors.length = 0;
    } else {
        this.errors = this.errors.filter(error => error.field !== fieldName);
    }
}
````
Fix:

Wipe out the existing errors and THEN add the remaining errors back.
````
clearErrors: function(fieldName = "") {
    if (!fieldName) {
        this.errors.length = 0;
    } else {
        let otherErrors = this.errors.filter(error => error.field !== fieldName);
        this.errors.length = 0;
        this.errors = otherErrors;
    }
}
````

## Uncaught syntax error "Unexpected token ."

Error:
````
CheckoutPage.js:44 Uncaught SyntaxError: Unexpected token .
````

Cause: Not using correct JSON object notation when setting an error message.
````
this.errors.push({field: "name", this.errorMessages.name.missing});
````

Fix:  Use object notation as intended: every element needs a name.
````
this.errors.push({field: "name", msg: this.errorMessages.name.missing});
````

## Typo in field during validation

Error:
````
[Vue warn]: Error in v-on handler: "TypeError: Expected string but received a undefined."

(found in <Root>)
warn @ vue.js:634
vue.js:1897 TypeError: Expected string but received a undefined.
    at assertString (validator.js:99)
    at Object.isCreditCard (validator.js:1082)
    at Vue.performChecks (CheckoutPage.js:88)
    at Vue.blurred (CheckoutPage.js:112)
    at invokeWithErrorHandling (vue.js:1863)
    at HTMLInputElement.invoker (vue.js:2188)
    at HTMLInputElement.original._wrapper (vue.js:7541)
````

Cause: A mis-named field causes validation to fail.
````
if (v.isEmpty(form.ccNumber)) {
    this.errors.push({field: "ccNumber", msg: msgs.ccNumber.missing});
} else if (!v.isCreditCard(form.ccnumber)) { // <--- error
    this.errors.push({field: "ccNumber", msg: msgs.ccNumber.value});
}
````

Fix: First step: fix the bad name.  optional step: catch exceptions and handle them.    

````
if (v.isEmpty(form.ccNumber)) {
    this.errors.push({field: "ccNumber", msg: msgs.ccNumber.missing});
} else if (!v.isCreditCard(form.ccNumber)) { // <--- fix
    this.errors.push({field: "ccNumber", msg: msgs.ccNumber.value});
}
````

## Bad Module Import

Error:
Blank page, console says:
````
GET http://localhost:8080/GreaterGoodsValidate/js/business/FieldError net::ERR_ABORTED 404
````

Cause: Lack of a .js extension when importing.
````
import {FieldError} from "../business/FieldError";
````

Fix:

````
import {FieldError} from "../business/FieldError.js";
````

### Bad expression

Error:
````
[Vue warn]: Error in render: "TypeError: cart.getNumberOfItems is not a function"
````

Cause: I changed this to be a getter.

Fix: Do a global search and replace on cart.getNumberOfItems.
Changed from this:
````
<template v-if="cart.getNumberOfItems() > 1">
````

to this:
````
<template v-if="cart.numberOfItems > 1">
````
