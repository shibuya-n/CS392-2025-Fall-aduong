//
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
//
// HX-2025-10-23:
// typedef LnStrm<T> = Supplier<LnStcn<T>>
//
class LnStrm<T> {
    private
    // final
    Supplier<LnStcn<T>> value = null;

    public LnStrm(Supplier<LnStcn<T>> fxs) {
	this.value = fxs;
    }

    public LnStcn<T> eval0() {
	final
	Supplier<LnStcn<T>>
	fstcn = this.value;
	this.value = null; return fstcn.get();
    }

    public void
	foritm0(Consumer<? super T> work) {
	LnStrmSUtil.foritm0(this, work); return;
    }

    public LnStrm<T>
	filter0(Predicate<? super T> pred) {
	return LnStrmSUtil.filter0(this, pred);
    }
}
