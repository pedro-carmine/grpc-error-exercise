package pt.tecnico.grpc.server;

import pt.tecnico.grpc.Banking.*;
import pt.tecnico.grpc.BankingServiceGrpc;
import static io.grpc.Status.INVALID_ARGUMENT;

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
        String client = consultRequest.getClient();

        if (!bank.isClient(client)) {
            responseObserver.onError(INVALID_ARGUMENT.withDescription("Input has to be a valid user!").asRuntimeException());
        }

        Integer balance = bank.getBalance(client);


        responseObserver.onNext(ConsultResponse.newBuilder().setResponse(balance).build());
        responseObserver.onCompleted();
    
	}

    public void fundAll(FundAllRequest fundRequest, StreamObserver<FundAllResponse> responseObserver) {
        Integer amount = fundRequest.getAmount();

        if (amount <= 0) {
            responseObserver.onError(INVALID_ARGUMENT.withDescription("Amount must be greater than zero!").asRuntimeException());
        }

        bank.fundAll(amount);

        responseObserver.onNext(FundAllResponse.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
