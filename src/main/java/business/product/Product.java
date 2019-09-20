/**
 * BSD 3-Clause License
 *
 * Copyright (C) 2018 Steven Atkinson <steven@nowucca.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package business.product;

import java.util.Date;

/**
 *
 */
public class Product {

    private long productId;
    private String name;
    private String description;
    private int price;
    private int points;
    private Date lastUpdate;
    private long categoryId;

    public Product(long productId, String name, String description, int price, int points, Date lastUpdate, Long categoryIdFromDb) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.points = points;
        this.lastUpdate = lastUpdate;
        this.categoryId = categoryIdFromDb;
    }

    public long getProductId() {
        return productId;
    }
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getPoints() {
        return points;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public String getDescription() {
        return description;
    }

    public long getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        return "business.product.Product[product_id=" + productId + "]";
    }

}
