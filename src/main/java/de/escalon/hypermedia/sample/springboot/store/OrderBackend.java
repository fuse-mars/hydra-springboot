package de.escalon.hypermedia.sample.springboot.store;


import org.springframework.stereotype.Component;

import de.escalon.hypermedia.sample.springboot.model.store.OrderModel;
import de.escalon.hypermedia.sample.springboot.model.store.OrderStatus;
import de.escalon.hypermedia.sample.springboot.model.store.OrderedItemModel;
import de.escalon.hypermedia.sample.springboot.model.store.ProductModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock order backend. Created by Dietrich on 17.02.2015.
 */
@Component
public class OrderBackend {
    public static int orderCounter;
    public static int orderedItemCounter;
    public List<OrderModel> orderModels = new ArrayList<OrderModel>();

    public OrderModel createOrder() {
        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderCounter++);
        orderModels.add(orderModel);
        return orderModel;
    }

    public OrderModel addOrderedItem(int orderId, ProductModel productModel) {
        OrderModel orderModel = orderModels.get(orderId);
        List<OrderedItemModel> products = orderModel.getOrderedItems();
        products.add(new OrderedItemModel(productModel, orderedItemCounter++));
        return orderModel;
    }

    public OrderModel orderAccessoryForOrderedItem(int orderId, int orderedItemId, ProductModel accessory) {
        OrderModel orderModel = orderModels.get(orderId);
        List<OrderedItemModel> products = orderModel.getOrderedItems();
        OrderedItemModel orderedItem = products.get(orderedItemId); // by index
        orderedItem.orderedItem.addAccessory(accessory);
        return orderModel;
    }

    public OrderModel getOrder(int id) {
        return orderModels.get(id);
    }

    public List<OrderModel> getOrders() {
        return orderModels;
    }

    public List<OrderModel> getOrdersByStatus(OrderStatus orderStatus) {
        List<OrderModel> ret = new ArrayList<OrderModel>();
        for (OrderModel orderModel : orderModels) {
            if (orderModel.getOrderStatus() == orderStatus) {
                ret.add(orderModel);
            }
        }
        return ret;
    }

    public void deleteOrderedItem(int orderId, int orderedItemId) {
        OrderModel order = getOrder(orderId);
        order.getOrderedItems()
                .remove(orderedItemId);
    }
}
