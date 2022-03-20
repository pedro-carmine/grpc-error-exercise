package pt.tecnico.grpc.server;

import pt.tecnico.grpc.Banking.*;
import pt.tecnico.grpc.BankingServiceGrpc;

import io.grpc.stub.StreamObserver;

public class BankingServiceImpl extends BankingServiceGrpc.BankingServiceImplBase {
	private Bank bank = new Bank();

	@Override
	public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
		bank.register(request.getClient(), request.getBalance());

		responseObserver.onNext(RegisterResponse.getDefaultInstance());
		responseObserver.onCompleted();
    }

    @Override
    public void consult(ConsultRequest consultRequest, StreamObserver<ConsultResponse> responseObserver) {
        Integer balance = bank.getBalance(consultRequest.getClient());

        responseObserver.onNext(ConsultResponse.newBuilder().setResponse(balance).build());
        responseObserver.onCompleted();
    
	}

}
