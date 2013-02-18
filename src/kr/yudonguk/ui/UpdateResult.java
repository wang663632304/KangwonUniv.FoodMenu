package kr.yudonguk.ui;

public class UpdateResult
{
	public boolean isSuccess;
	public String errorMessage;

	public UpdateResult()
	{
		this(true);
	}

	public UpdateResult(boolean isSuccess_)
	{
		this(isSuccess_, "");
	}

	public UpdateResult(boolean isSuccess_, String errorMessage_)
	{
		isSuccess = isSuccess_;
		errorMessage = errorMessage_;
	}
}
