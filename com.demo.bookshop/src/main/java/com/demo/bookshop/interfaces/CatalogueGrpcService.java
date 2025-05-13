package com.demo.bookshop.interfaces;

import java.util.List;

import com.demo.bookshop.application.dto.BookDTO;
import com.demo.bookshop.application.service.GetBookService;
import com.demo.bookshop.application.service.ListBooksService;
import com.demo.bookshop.grpc.CatalogueGrpc;
import com.demo.bookshop.grpc.ListBooksResponse;
import com.demo.bookshop.grpc.GetBookRequest;
import com.demo.bookshop.grpc.GetBookResponse;
import com.google.protobuf.Empty;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;

@GrpcService
public class CatalogueGrpcService extends CatalogueGrpc.CatalogueImplBase {
    @Inject
    ListBooksService listBooksSvc;
    @Inject
    GetBookService getBookSvc;

    @Override
    @Blocking
    public void listBooks(Empty request, StreamObserver<ListBooksResponse> responseObserver) {
        try {
            List<BookDTO> books = listBooksSvc.handle();
            ListBooksResponse.Builder responseBuilder = ListBooksResponse.newBuilder();
            for (BookDTO book : books) {
                responseBuilder.addBooks(bookToProto(book));
            }
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            e.printStackTrace(); // または LOG に出す
            responseObserver.onError(Status.INTERNAL
                .withDescription("Unexpected error in listBooks")
                .withCause(e)
                .asRuntimeException());
        }
    }

    @Override
    @Blocking
    public void getBook(GetBookRequest request, StreamObserver<GetBookResponse> responseObserver) {
        try {
            BookDTO book = getBookSvc.handle(request.getId());
            GetBookResponse response = GetBookResponse.newBuilder()
                .setBook(bookToProto(book))
                .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            e.printStackTrace(); // または LOG に出す
            responseObserver.onError(Status.INTERNAL
                .withDescription("Unexpected error in listBooks")
                .withCause(e)
                .asRuntimeException());
        }
    }

    private com.demo.bookshop.grpc.Book bookToProto(BookDTO book) {
        return com.demo.bookshop.grpc.Book.newBuilder()
            .setId(book.getId())
            .setTitle(book.getTitle())
            .setAuthor(book.getAuthor())
            .setPrice(book.getPrice())
            .build();
    }
}
