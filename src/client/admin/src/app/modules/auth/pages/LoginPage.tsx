import {Box, Button, Card, CardContent, CardHeader, TextField, Typography} from "@mui/material";
import {useLoginDefault, UserLoginModel} from "@/app/usecases/auth.usecase.ts";
import {useForm} from "react-hook-form";
import {LoadingButton} from "@mui/lab";

const LoginPage = () => {
    const { mutate, isLoading } = useLoginDefault()
    const {register, handleSubmit} = useForm<UserLoginModel>()
    
    return <form 
        onSubmit={handleSubmit(data => {
            mutate({
                username: data?.username,
                password: data?.password
            })
        })}
    >
        <Box>
            
            <Card
                sx={{maxWidth: "400px", margin: "auto", marginTop: "50px", border: "2px"}}
            >
                <CardHeader title={<Typography align={"center"} variant={"h5"}>Quản trị </Typography>} />
                <CardContent sx={{
                    display: "flex",
                    flexDirection: "column",
                    gap: "20px"
                }}>
                    <TextField label={"Tên đăng nhập "} fullWidth={true} {...register("username")} />
                    <TextField type={"password"} label={"Mật khẩu "} fullWidth={true} {...register("password")} />
                    <LoadingButton loading={isLoading} type={"submit"} color={"error"} variant={"contained"}>Đăng nhập </LoadingButton>
                </CardContent>
            </Card>
        </Box>
    </form>
}

export default LoginPage