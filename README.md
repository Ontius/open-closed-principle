# Open/Closed Principle

This repository explains the `Open/Closed Principle` from the `SOLID principles`. There are two
main `packages`, `ch.zhaw.solid.clean` and `ch.zhaw.solid.spaghetti`. The first contains clean
code as the supposed by the `Open/Closed Principle`. The `spaghetti package` contains `spaghetti`
code.

## REST API

Try out the `Rest API`. There are two `Rest Controllers`:

- `api/clean/order`
- `api/spaghetti/order`

You can access them via `http://localhost:8080/api/clean/order` and
`http://localhost:8080/api/spaghetti/order`.

Use this `JSON snippet` to create an `Order`.

```json
{
    "lineItems": [
        {
            "description": "iPhone 12 Pro",
            "price": 999.0,
            "quantity": 1,
            "weight": 1.0
        },
        {
            "description": "Samsung Galaxy S20 Ultra",
            "price": 1010.0,
            "quantity": 1,
            "weight": 1.0
        }
    ],
    "shippingMethod": "POST"
}
```
